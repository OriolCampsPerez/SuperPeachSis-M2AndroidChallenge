package helloandroid.ut3.challengeandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import helloandroid.ut3.challengeandroid.R;

public abstract class ResourceFetcher {

    public static final int NB_RUN_SPRITES = 20;
    public static final int NB_JUMP_SPRITES = 30;

    private static Bitmap convertToBitmap(int resource, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), resource);
    }

    public static List<Bitmap> getEnemiesBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.enemypeach1, context));
        resList.add(convertToBitmap(R.drawable.enemypeach2, context));
        resList.add(convertToBitmap(R.drawable.enemybanana1, context));
        resList.add(convertToBitmap(R.drawable.enemybanana2, context));
        return resList;
    }

    public static List<Bitmap> getGameCharacterRunBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.maincharacter_run1, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run2, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run3, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run4, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run5, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run6, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run7, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run8, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run9, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run10, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run11, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run12, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run13, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run14, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run15, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run16, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run17, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run18, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run19, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_run20, context));
        return resList;
    }

    public static List<Bitmap> getGameCharacterJumpBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.maincharacter_jump1, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump2, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump3, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump4, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump5, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump6, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump7, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump8, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump9, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump10, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump11, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump12, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump13, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump14, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump15, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump16, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump17, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump18, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump19, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump20, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump21, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump22, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump23, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump24, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump25, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump26, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump27, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump28, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump29, context));
        resList.add(convertToBitmap(R.drawable.maincharacter_jump30, context));
        return resList;
    }

    public static List<Bitmap> getGhostsBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.waterghost1, context));
        resList.add(convertToBitmap(R.drawable.waterghost2, context));
        resList.add(convertToBitmap(R.drawable.waterghost3, context));
        resList.add(convertToBitmap(R.drawable.waterghost4, context));
        return resList;
    }

    public static List<Bitmap> getWallsBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.mushroomwall1, context));
        resList.add(convertToBitmap(R.drawable.mushroomwall2, context));
        return resList;
    }

    public static Bitmap getBackgroundBitmap(Context context) {
        return convertToBitmap(R.drawable.background, context);
    }

    public static List<Bitmap> getCloudsBitmap(Context context) {
        List<Bitmap> resList = new ArrayList<>();
        resList.add(convertToBitmap(R.drawable.cloud1, context));
        resList.add(convertToBitmap(R.drawable.cloud2, context));
        resList.add(convertToBitmap(R.drawable.cloud3, context));
        resList.add(convertToBitmap(R.drawable.cloud4, context));
        return resList;
    }
}
