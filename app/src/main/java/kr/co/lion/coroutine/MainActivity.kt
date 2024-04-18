package kr.co.lion.coroutine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.coroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var visitConsultingViewModel: VisitConsultingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        visitConsultingViewModel = ViewModelProvider(this).get(VisitConsultingViewModel::class.java)

        activityMainBinding.visitConsultingViewModel = visitConsultingViewModel
        activityMainBinding.lifecycleOwner = this


        setContentView(activityMainBinding.root)

        activityMainBinding.consultingAddButton.setOnClickListener {
            updateData()
        }
    }
    fun updateData(){
        CoroutineScope(Dispatchers.Main).launch {
            val dataSequence = VisitConsultingDao.getDataSequence()

            VisitConsultingDao.updateDataSequence(dataSequence + 1)

            val visitConsultingId = dataSequence + 1
            val centerId = ""
            val trainerId = ""
            val name = visitConsultingViewModel.editTextNameConsulting.value!!
            val exercisePurpose = visitConsultingViewModel.editTextPurposeConsulting.value!!
            val applicationTime = visitConsultingViewModel.editTextDateConsulting.value!!
            val etcContent = visitConsultingViewModel.editTextEtcConsulting.value

            val stateCheck = true

            val visitConsultingModel = VisitConsultingModel(visitConsultingId, centerId, trainerId,
                name, exercisePurpose, applicationTime, etcContent, stateCheck)

            VisitConsultingDao.insertApplication(visitConsultingModel)


            val intent = Intent(this@MainActivity, VisitConsultingActivity::class.java)
            startActivity(intent)
        }
    }
}

