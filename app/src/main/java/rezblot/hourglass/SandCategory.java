package rezblot.hourglass;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by rezblot on 9/23/17.
 */

//https://developer.android.com/training/data-storage/room/defining-data.html

//@Entity(indices = {@Index("name"),
//@Index(value = {"last_name", "address"})})
/*
* @Entity(foreignKeys = @ForeignKey(entity = User.class,
*         parentColumns = "id",         other entity
*         childColumns = "user_id"))    this entity
*/

@Entity
public class SandCategory {
    @PrimaryKey
    private int Id;

    //@ColumnInfo(name = "Name")
    public String Name;

    public int getId() {return this.Id; }

    public String getName() {
        return this.Name;
    }

    public void setId(int id) { this.Id = id; }

    public void setName(String name) {
        Name = name;
    }



    //@Ignore
    //private boolean running = false;
}
