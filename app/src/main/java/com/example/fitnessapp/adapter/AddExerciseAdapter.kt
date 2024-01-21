package com.example.fitnessapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fitnessapp.MyApplication
import com.example.fitnessapp.R
import com.example.fitnessapp.data.AppDatabase
import com.example.fitnessapp.data.GymExercise
import com.example.fitnessapp.data.WorkoutViewModel

class AddExerciseAdapter
    (var exercises: List<GymExercise>, private val myApplication: MyApplication,
     val viewModel: WorkoutViewModel,
     private val switchToDetailCallback: (GymExercise) -> Unit): RecyclerView.Adapter<AddExerciseAdapter.ExerciseViewHolder>() {

    val appDatabase: AppDatabase = myApplication.database

    fun setData(newData: List<GymExercise>) {
        exercises = newData
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle2)
        val tvClass = itemView.findViewById<TextView>(R.id.tvClass2)
        val imageViewMuscle = itemView.findViewById<ImageView>(R.id.imageViewMuscle2)
        val addBtn = itemView.findViewById<ImageButton>(R.id.add_btn)
        // val addBtn = itemView.findViewById<Button>(R.id.addBtn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddExerciseAdapter.ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_add_exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: AddExerciseAdapter.ExerciseViewHolder, position: Int) {
        var exercise = exercises[position]
        holder.tvTitle.setTextIsSelectable(true)
        holder.tvTitle.isSelected
        holder.tvTitle.text = exercise.name
        holder.tvClass.text = exercise.bodyPart
        holder.imageViewMuscle.load(exercise.gifUrl)


        holder.itemView.setOnClickListener{
            switchToDetailCallback(exercise)
        }

        holder.addBtn.setOnClickListener{
            viewModel.updateCurrentWorkout(exercise)
            Log.d("addExerciseBtn","exercise btn pressed")
        }
    }
}