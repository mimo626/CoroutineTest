package kr.co.lion.coroutine

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VisitConsultingDao {
    companion object{
        // 방문상담 번호 시퀀스값을 가져온다.
        suspend fun getDataSequence():Int{
            var dataSequence = -1

            CoroutineScope(Dispatchers.IO).launch {
                dataSequence = FirebaseFirestore.getInstance().collection("Sequence")
                    .document("DataSequence")
                    .get().await()
                    .getLong("value")?.toInt()!!
            }.join()

            return dataSequence
        }

        // 방문상담 번호 시퀀스 값을 업데이트 한다.
        suspend fun updateDataSequence(dataSequence: Int){
            CoroutineScope(Dispatchers.IO).launch {
                val documentReference = FirebaseFirestore.getInstance().collection("Sequence")
                    .document("DataSequence")

                val map = mutableMapOf<String, Long>()
                map["value"] = dataSequence.toLong()

                documentReference.set(map)
            }.join()
        }

        // 게시글 정보를 저장한다.
        suspend fun insertApplication(visitConsultingModel: VisitConsultingModel){
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .add(visitConsultingModel)
            }.join()
        }

        // id를 이용해 데이터를 가져와 반환한다.
        suspend fun getApplication(visitConsultingId:Int):VisitConsultingModel{
            var visitConsultingModel:VisitConsultingModel? = null

            CoroutineScope(Dispatchers.IO).launch {
                val queryShapshot = FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .whereEqualTo("visitConsultingId", visitConsultingId).get().await()

                visitConsultingModel = queryShapshot.documents[0].toObject(VisitConsultingModel::class.java)
            }.join()
            return visitConsultingModel!!
        }

        suspend fun getDataList():MutableList<VisitConsultingModel>{
            // 게시글 정보를 담을 리스트
            val dataList = mutableListOf<VisitConsultingModel>()

            CoroutineScope(Dispatchers.IO).launch {
                val query = FirebaseFirestore.getInstance().collection("VisitConsulting")
                    .orderBy("visitConsultingId")

                val querySnapshot = query.get().await()

                querySnapshot.forEach{
                    val visitConsultingModel = it.toObject(VisitConsultingModel::class.java)
                    dataList.add(visitConsultingModel)
                }
            }.join()

            return dataList
        }
    }
}