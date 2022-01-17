package js.personal.exifeditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import js.personal.exifeditor.databinding.EditorMainBinding

class EditorActivity : AppCompatActivity() {

    private lateinit var picker: Button
    private lateinit var apply: Button

    private lateinit var importName: String
    private lateinit var importCreated: String
    private lateinit var importModified: String
    private lateinit var importResolution: String
    private lateinit var exportModified: String

    private lateinit var binding : EditorMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.editor_main)
    }
}