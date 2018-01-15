package rezblot.hourglass;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by rezblot on 1/1/18.
 */

//https://developer.android.com/training/data-storage/room/accessing-data.html

/*
 Note: Room doesn't support database access on the main thread
 unless you've called allowMainThreadQueries() on the builder
 because it might lock the UI for a long period of time.
 Asynchronous queries—queries that return instances of
 LiveData or Flowable—are exempt from this rule because
 they asynchronously run the query on a background thread when needed.
 */


@Dao
public interface SandCategoryDao {
    @Query("SELECT * FROM SandCategory")
    List<SandCategory>  getAll();

    @Query("SELECT * FROM SandCategory WHERE id IN (:sandCategoryIds)")
    List<SandCategory> getAllByIds(int[] sandCategoryIds);

    //Can I make this into better criteria?
    //@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
    //        + "last_name LIKE :last LIMIT 1")
    //SandCategory findByName(String first, String last);

    //https://sqlite.org/lang_conflict.html
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSandCategories(SandCategory... sandCategories);


    @Insert
    void insertAll(SandCategory... sandCategories);

    //Does every field need to match?
    @Delete
    void delete(SandCategory sandCategory);
}
