package js.personal.exifeditor

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import js.personal.exifeditor.databinding.StartMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: StartMainBinding

    private lateinit var picker: Button
    private lateinit var apply: Button

    private lateinit var importName: String
    private lateinit var importCreated: String
    private lateinit var importModified: String
    private lateinit var importResolution: String
    private lateinit var exportModified: String

    private lateinit var array: List<Uri>

    val permissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.start_main)

        checkPermissions()
    }

    private fun checkPermissions() {
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면... 권한 요청
        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), 89)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 89) {
            if(grantResults.isNotEmpty()) {
                for((i, permission) in permissions.withIndex()) {
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                        finishAffinity()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setModifiedDate() {
        val fileName = "c:\\test\\google.png"

        val file: Path = Paths.get(fileName)
        val attr: BasicFileAttributes = Files.readAttributes(file, BasicFileAttributes::class.java)
        val lastModifiedTime: FileTime = attr.lastModifiedTime()

        // print original last modified time

        // print original last modified time
        println("[original] lastModifiedTime:$lastModifiedTime")

        val newLocalDate: LocalDate = LocalDate.of(1998, 9, 30)
        // convert LocalDate to instant, need time zone
        // convert LocalDate to instant, need time zone
        val instant: Instant = newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()

        // convert instant to filetime
        // update last modified time

        // convert instant to filetime
        // update last modified time
        Files.setLastModifiedTime(file, FileTime.from(instant))

        // read last modified time again

        // read last modified time again
        val newLastModifiedTime: FileTime = Files.readAttributes(
            file,
            BasicFileAttributes::class.java
        ).lastModifiedTime()
        println("[updated] lastModifiedTime:$newLastModifiedTime")
    }
}