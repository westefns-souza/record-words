package com.westefns.recordswords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.westefns.recordswords.dao.RecordWordDao;
import com.westefns.recordswords.model.RecordWord;

public class AlertDialogConfirmDelete extends AppCompatDialogFragment {
    private RecordWordDao recordWordDao;
    private RecordWord recordWord;

    public AlertDialogConfirmDelete(RecordWordDao recordWordDao, RecordWord recordWord) {
        this.recordWordDao = recordWordDao;
        this.recordWord = recordWord;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage("Tem certeza que quer deletar essa palavra?")
                .setPositiveButton("Sim, Deletar", (dialog, id) -> {
                    boolean recordDeleted = recordWordDao.delete(recordWord);

                    if (recordDeleted) {
                        Intent it = new Intent(getActivity(), MainActivity.class);
                        startActivity(it);
                    }
                }).setNegativeButton("Não Deletar", (dialog, id) -> {
                    dialog.dismiss();
                });

        return builder.create();
    }
}
