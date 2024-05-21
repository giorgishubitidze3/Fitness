package com.example.fitnessapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.adapter.CurrentSessionAdapter
import com.example.fitnessapp.data.GymExercise
import com.example.fitnessapp.data.WorkoutViewModel


class CurrentSessionFragment : Fragment() {
    private lateinit var viewModel: WorkoutViewModel
    private lateinit var cancelDialogBuilder: AlertDialog.Builder
    private lateinit var finishDialogBuilder: AlertDialog.Builder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_session, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelDialogBuilder = AlertDialog.Builder(requireContext())
        finishDialogBuilder = AlertDialog.Builder(requireContext())

        viewModel= ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        val finishButton = view.findViewById<Button>(R.id.buttonFinish)
        val navController = activity?.findNavController(R.id.fragment_container)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        val buttonCancel = view.findViewById<Button>(R.id.btnCancel)
        val buttonAdd = view.findViewById<Button>(R.id.btnAdd)

        var listCurrentWorkouts = emptyList<GymExercise>()

        val exercisesAdapter = CurrentSessionAdapter(viewModel, viewLifecycleOwner, requireContext())

        viewModel.currentWorkouts.observe(viewLifecycleOwner){
            Log.d("Observer", "Called")
            exercisesAdapter.setData(it)
            listCurrentWorkouts = it
        }


//        viewModel.currentWorkouts.observe(viewLifecycleOwner) { exercises ->
//            exercisesAdapter.setData(exercises)
//        }

        val recyclerViewCurrent = view.findViewById<RecyclerView>(R.id.recyclerViewCurrentSession)
        recyclerViewCurrent.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCurrent.adapter = exercisesAdapter


        cancelDialogBuilder.setTitle("Cancel Workout")
        cancelDialogBuilder.setMessage("Are you sure you want to cancel this workout?")


        //finishButton
        //alertBoxYES
        cancelDialogBuilder.setPositiveButton(android.R.string.yes){  _, _ ->
                navController?.navigate(R.id.sessionFragment)
                viewModel.stopTimer()
                viewModel.endWorkout()
                exercisesAdapter.clearData()
                Log.d("ALERTBOX","yes clicked")

        }

        //alertBoxNO
        cancelDialogBuilder.setNegativeButton(android.R.string.no) { _, _  ->
            Toast.makeText(requireContext(),
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }


        //Cancel Button
        buttonCancel.setOnClickListener {
            //TODO modify later
            cancelDialogBuilder.show()
        }

        val slideDownButton = view.findViewById<ImageButton>(R.id.slide_down_button)
        val currentTimer = view.findViewById<TextView>(R.id.currentTimer)


        finishDialogBuilder.setTitle("Finish Workout")
        finishDialogBuilder.setMessage("Are you sure you want to finish this workout?")

        finishDialogBuilder.setPositiveButton(android.R.string.yes){  _, _ ->
            navController?.navigate(R.id.sessionFragment)
            viewModel.stopTimer()
            viewModel.endWorkout()
            exercisesAdapter.clearData()
            Log.d("ALERTBOX","yes clicked")

        }

        //alertBoxNO
        finishDialogBuilder.setNegativeButton(android.R.string.no) { _, _  ->
            Toast.makeText(requireContext(),
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }





        viewModel.elapsedtime.observe(viewLifecycleOwner) { formattedTime ->
            currentTimer.text = formattedTime
        }

        finishButton.setOnClickListener { finishDialogBuilder.show() }

        slideDownButton.setOnClickListener {
            navController?.navigate(R.id.sessionFragment)
        }


        buttonAdd.setOnClickListener {
            navController?.navigate(R.id.workoutPicker)
        }
}

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("CurrentSession", "onDestroyView called")

    }

}