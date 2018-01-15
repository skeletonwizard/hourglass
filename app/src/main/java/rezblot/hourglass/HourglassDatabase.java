package rezblot.hourglass;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by rezblot on 1/1/18.
 */

//https://developer.android.com/training/data-storage/room/index.html

@Database(entities = {SandCategory.class}, version = 1)
public abstract class HourglassDatabase extends RoomDatabase {
    public abstract SandCategoryDao SandCategoryDao();
}
