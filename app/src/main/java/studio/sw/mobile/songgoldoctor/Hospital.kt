package studio.sw.mobile.songgoldoctor

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.sql.Time

@Parcelize
class Hospital(
    var name:String,
    var phoneNumber:String,
    var id:String,
    var address:String,
    var position:LatLng,
    var workDays: ArrayList<WorkDay>,
    var departments: ArrayList<Department>): Parcelable