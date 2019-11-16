package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.kolllor3.lijnhaltecopanian.database.FavoriteHalteDao;
import com.kolllor3.lijnhaltecopanian.database.HalteDataBase;
import com.kolllor3.lijnhaltecopanian.database.TimeTableDataBase;
import com.kolllor3.lijnhaltecopanian.interfaces.WorkerCallback;
import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.providers.LijnApiProider;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

public class DienstRegelingBackgroundWorker extends ListenableWorker {

    private LijnApiProider lijnApiProider;
    private FavoriteHalteDao favoriteHalteDao;
    private List<Halte> favHaltes;

    public DienstRegelingBackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        TimeTableDataBase db = TimeTableDataBase.getDatabase(context);
        HalteDataBase halteDB = HalteDataBase.getDatabase(context);
        favoriteHalteDao = halteDB.getFavoriteHalteDao();

        lijnApiProider = new LijnApiProider(db.getTimeTableDao());
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return CallbackToFutureAdapter.getFuture(completer -> {
            WorkerCallback callback = new WorkerCallback() {
                int succesCounter = 0;

                @Override
                public void onComplete() {
                    if(favHaltes.size() - 1 >= succesCounter){
                        onAllCompleted();
                    }else{
                        succesCounter++;
                    }
                }

                @Override
                public void onAllCompleted() {
                    completer.set(Result.success());
                }

                @Override
                public void onFailure() {
                    completer.set(Result.failure());
                }
            };

            Utilities.doInBackground(()->{
                favHaltes = favoriteHalteDao.getAllFavoriteHaltesToList();
                if(favHaltes.size() > 0) {
                    for (Halte halte : favHaltes) {
                        lijnApiProider.getDienstRegeling(halte.getHaltenummer(), halte.getEntiteitnummer(), callback);
                    }
                }else{
                    callback.onAllCompleted();
                }
            });
            return callback;
        });

    }
}
