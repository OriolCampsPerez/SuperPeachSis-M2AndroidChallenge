package helloandroid.ut3.challengeandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import helloandroid.ut3.challengeandroid.R;

public abstract class ResourceFetcher {
    private static Bitmap convertToBitmap(int ressource, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), ressource);
    }

    public static List<Bitmap> getEnemiesBitmap (Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.enemypeach1, context));
        resList.add(convertToBitmap(R.drawable.enemypeach2, context));
        resList.add(convertToBitmap(R.drawable.enemybanana1, context));
        resList.add(convertToBitmap(R.drawable.enemybanana2, context));
        return resList;
    }

    public static List<Bitmap> getGameCharacterBitmap (Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.maincharacter, context));
        return resList;
    }

    public static List<Bitmap> getGhostsBitmap (Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.waterghost1, context));
        resList.add(convertToBitmap(R.drawable.waterghost2, context));
        resList.add(convertToBitmap(R.drawable.waterghost3, context));
        resList.add(convertToBitmap(R.drawable.waterghost4, context));
        return resList;
    }

    public static List<Bitmap> getWallsBitmap (Context context) {
        List<Bitmap> resList = new ArrayList<>();
        // TODO: Add wall images
        return resList;
    }

    public static Bitmap getBackgroundBitmap(Context context) {
        return convertToBitmap(R.drawable.highbackground, context);
    }
}
