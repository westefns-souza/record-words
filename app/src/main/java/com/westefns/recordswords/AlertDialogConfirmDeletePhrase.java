package com.westefns.recordswords;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.westefns.recordswords.dao.PhraseExampleDao;
import com.westefns.recordswords.model.PhraseExample;

public class AlertDialogConfirmDeletePhrase extends AppCompatDialogFragment {
    private final PhraseExampleDao phraseExampleDao;
    private final PhraseExample phraseExample;

    public AlertDialogConfirmDeletePhrase(PhraseExampleDao phraseExampleDao, PhraseExample phraseExample) {
        this.phraseExampleDao = phraseExampleDao;
        this.phraseExample = phraseExample;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setMessage("Tem certeza que quer deletar essa frase?")
                .setPositiveButton("Sim, Deletar", (dialog, id) -> {
                    boolean recordDeleted = phraseExampleDao.delete(phraseExample);

                    if (recordDeleted) {
                        Intent it = new Intent(getActivity(), MainActivity.class);
                        startActivity(it);
                    }
                }).setNegativeButton("Não Deletar", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }
}
