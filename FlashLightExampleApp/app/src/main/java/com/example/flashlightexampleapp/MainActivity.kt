package com.example.flashlightexampleapp

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.flashlightexampleapp.R.drawable
import com.example.flashlightexampleapp.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var torchState : Boolean = false
    private lateinit var cameraManager : CameraManager
    private var cameraId : String = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0]



        Dexter.withContext(this).withPermission(android.Manifest.permission.CAMERA).withListener(object : PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                turnOnFlishLight()

            }



            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(this@MainActivity,"please grant the permission",Toast.LENGTH_LONG).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }

        }).check()

    }
    private fun turnOnFlishLight(){
        binding.imgTorch.setOnClickListener {
            torchState = when(torchState){
                false ->{
                    cameraManager.setTorchMode(cameraId,true)
                    binding.imgTorch.setImageResource(R.drawable.flashlight_on)
                    true
                }

                true ->{
                    cameraManager.setTorchMode(cameraId,false)
                    binding.imgTorch.setImageResource(R.drawable.flashlight_off)
                    false
                }
            }
        }
    }
}