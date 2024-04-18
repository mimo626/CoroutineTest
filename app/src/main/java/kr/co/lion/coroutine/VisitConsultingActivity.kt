package kr.co.lion.coroutine

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.coroutine.databinding.ActivityVisitConsultingBinding
import kr.co.lion.coroutine.databinding.RowVisitconsultingBinding

class VisitConsultingActivity : AppCompatActivity() {
    lateinit var activityVisitConsultingBinding: ActivityVisitConsultingBinding
    var visitList = mutableListOf<VisitConsultingModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVisitConsultingBinding = ActivityVisitConsultingBinding.inflate(layoutInflater)
        gettingData()
        settingRecyclerView()

        activityVisitConsultingBinding.btnVisit.setOnClickListener {
            finish()
        }

        setContentView(activityVisitConsultingBinding.root)
    }


    fun settingRecyclerView(){
        activityVisitConsultingBinding.apply {
            recyclerViewVisit.apply {
                adapter = VisitRecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@VisitConsultingActivity)

                val deco = MaterialDividerItemDecoration(this@VisitConsultingActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class VisitRecyclerViewAdapter:RecyclerView.Adapter<VisitRecyclerViewAdapter.VisitViewHolder>(){
        inner class VisitViewHolder(rowVisitconsultingBinding: RowVisitconsultingBinding):RecyclerView.ViewHolder(rowVisitconsultingBinding.root){
            val rowVisitconsultingBinding:RowVisitconsultingBinding

            init {
                this.rowVisitconsultingBinding = rowVisitconsultingBinding

                rowVisitconsultingBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
            val rowVisitconsultingBinding = RowVisitconsultingBinding.inflate(layoutInflater)
            val visitViewHolder = VisitViewHolder(rowVisitconsultingBinding)

            return visitViewHolder
        }

        override fun getItemCount(): Int {
            return visitList.size
        }

        override fun onBindViewHolder(holder: VisitViewHolder, position: Int) {
            holder.rowVisitconsultingBinding.textViewName.text = visitList[position].name
            holder.rowVisitconsultingBinding.textViePurpose.text = visitList[position].exercisePurpose
            holder.rowVisitconsultingBinding.textViewTime.text = visitList[position].applicationTime
            holder.rowVisitconsultingBinding.textViewEtc.text = visitList[position].etcContent
        }
    }

    fun gettingData(){
        CoroutineScope(Dispatchers.Main).launch {
            visitList = VisitConsultingDao.getDataList()

            // 데이터를 가져온 후에 어댑터에 데이터 변경을 알림
            activityVisitConsultingBinding.recyclerViewVisit.adapter?.notifyDataSetChanged()
        }
    }
}