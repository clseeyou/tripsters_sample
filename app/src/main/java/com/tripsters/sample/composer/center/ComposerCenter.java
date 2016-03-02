package com.tripsters.sample.composer.center;

import android.content.Context;
import android.content.SharedPreferences;

import com.tripsters.sample.composer.BaseComposer;
import com.tripsters.sample.composer.BaseComposer.ComposerType;
import com.tripsters.sample.composer.ComposerFactory;

import java.util.ArrayList;
import java.util.List;

public class ComposerCenter {

    private static final String DRAFT_SP = "draft_sp";

    private static final String TYPE_QUESTION = "type_question";
    private static final String TYPE_ANSWER = "type_answer";
    private static final String TYPE_REANSWER = "type_reanswer";

    private static ComposerCenter sInstance = null;

    private boolean mInit;

    private ComposerCenter() {
    }

    public synchronized static ComposerCenter getInstance() {
        if (sInstance == null) {
            sInstance = new ComposerCenter();
        }

        return sInstance;
    }

    public List<BaseComposer> getDrafts(Context context, ComposerType type, String uid) {
        List<BaseComposer> composers = new ArrayList<BaseComposer>();

        BaseComposer composer = ComposerFactory.create(type.getValue());
        composer.setContent(getDraftFromSp(context, type));

        composers.add(composer);

        return composers;
    }

    public void saveDraft(Context context, BaseComposer composer) {
        saveDraftToSp(context, composer.getContent(), composer.getType());
    }

    public void removeDraft(Context context, BaseComposer composer) {
        saveDraftToSp(context, "", composer.getType());
    }

    private static void saveDraftToSp(Context context, String text, ComposerType type) {
        SharedPreferences sp = context.getSharedPreferences(DRAFT_SP, 0);
        sp.edit().putString(getKeyFromType(type), text).commit();
    }

    private static String getDraftFromSp(Context context, ComposerType type) {
        SharedPreferences sp = context.getSharedPreferences(DRAFT_SP, 0);
        return sp.getString(getKeyFromType(type), "");
    }

    private static String getKeyFromType(ComposerType type) {
        if (type == ComposerType.SEND_ANSWER) {
            return TYPE_ANSWER;
        }

        if (type == ComposerType.SEND_REANSWER) {
            return TYPE_REANSWER;
        }

        return TYPE_QUESTION;
    }
}
