package com.tripsters.sample.composer;

import android.content.Context;
import android.content.Intent;

import com.tripsters.sample.composer.BaseComposer.ComposerType;
import com.tripsters.sample.util.Constants;

public class ComposerFactory {

    public static BaseComposer create(Context context, Intent intent) {
        BaseComposer composer = intent.getParcelableExtra(Constants.Extra.COMPOSER);

        if (composer == null) {
            composer =
                    create(intent.getIntExtra(Constants.Extra.COMPOSER_TYPE,
                            ComposerType.SENT_QUESTION.value));

            composer.init(context, intent);
        }

        return composer;
    }

    public static BaseComposer create(int value) {
        BaseComposer composer;
        ComposerType type = ComposerType.getType(value);

        switch (type) {
            case SENT_QUESTION:
                composer = new SendQuestionComposer();
                break;
            case SEND_ANSWER:
                composer = new SendAnswerComposer();
                break;
            case SEND_REANSWER:
                composer = new SendReAnswerComposer();
                break;
            default:
                throw new IllegalArgumentException("value is illegal : value = " + value);
        }

        composer.setType(type);

        return composer;
    }
}
