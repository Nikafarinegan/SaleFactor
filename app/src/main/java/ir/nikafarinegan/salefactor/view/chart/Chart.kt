package ir.nikafarinegan.salefactor.view.chart

import android.app.Activity
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import ir.nikafarinegan.salefactor.R

class Chart(
    private val activity: Activity
){
    fun create(
        chart: PieChart,
        entries: ArrayList<PieEntry>
    ){
        config(chart)
        setData(chart, entries)
    }

    private fun config(chart: PieChart) {
        chart.setUsePercentValues(false)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)

        chart.dragDecelerationFrictionCoef = 0.95f

//        chart.setCenterTextTypeface(ResourcesCompat.getFont(activity, R.font.iran_sans_mobile))
//        chart.centerText = "روزانه"

        chart.isDrawHoleEnabled = false
//        chart.setHoleColor(Color.WHITE)

//        chart.setTransparentCircleColor(Color.WHITE)
//        chart.setTransparentCircleAlpha(110)

//        chart.holeRadius = 58f
//        chart.transparentCircleRadius = 61f

//        chart.setDrawCenterText(true)

        chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        chart.animateY(1400, Easing.EaseInOutQuad);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

//        chart.setEntryLabelColor(Color.BLACK)
//        chart.setEntryLabelTypeface(ResourcesCompat.getFont(activity, R.font.iran_sans_mobile))
        chart.setEntryLabelTextSize(0f)
    }

    private fun setData(
        chart: PieChart,
        entries: ArrayList<PieEntry>
    ) {
        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 1f
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(activity, R.color.orange_300))
        colors.add(ContextCompat.getColor(activity, R.color.blue_200))
        colors.add(ContextCompat.getColor(activity, R.color.red_200))
        colors.add(ContextCompat.getColor(activity, R.color.green_A200))
        dataSet.colors = colors
        val data = PieData(dataSet)
//        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        data.setValueTypeface(ResourcesCompat.getFont(activity, R.font.iran_sans_mobile))
        chart.data = data
        chart.highlightValues(null)
        chart.invalidate()
    }
}