package com.syncstorm.hability.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boyzdroizy.simpleandroidbarchart.SimpleBarChart
import com.syncstorm.hability.R
import kotlin.random.Random
import com.github.mikephil.charting.utils.ColorTemplate

import android.R.attr.data
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.syncstorm.hability.database.DatabaseHandler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsDayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsTasksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val db = DatabaseHandler(context)
        val dbData = db.readTask()
        val statsC = TaskStatsController()

        val sumTaskCategories = statsC.getSumTaskCategories(dbData)
        val daySumTaskCategories = statsC.getDaySumTaskCategories(dbData)
        val monthSumTaskCategories = statsC.getMonthSumTaskCategories(dbData, context)
        val barChart: BarChart = view.findViewById(R.id.barChartTasks)
        val dayBarChart: BarChart = view.findViewById(R.id.barChartDayTasks)
        val monthBarChart: BarChart = view.findViewById(R.id.barChartMonthTasks)
        val pieChart: PieChart = view.findViewById(R.id.pieChartStats)
        val dayPieChart: PieChart = view.findViewById(R.id.pieChartDayStats)
        val monthPieChart: PieChart = view.findViewById(R.id.pieChartMonthStats)
        val scHelper = StatsChartHelper()
        scHelper.barChart(barChart, sumTaskCategories)
        scHelper.barChart(dayBarChart, daySumTaskCategories)
        scHelper.barChart(monthBarChart, monthSumTaskCategories)
        scHelper.pieChart(pieChart, sumTaskCategories)
        scHelper.pieChart(dayPieChart, daySumTaskCategories)
        scHelper.pieChart(monthPieChart, monthSumTaskCategories)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsDayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatsTasksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}