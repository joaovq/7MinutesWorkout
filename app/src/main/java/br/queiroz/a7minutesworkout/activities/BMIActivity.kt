package br.queiroz.a7minutesworkout.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.queiroz.a7minutesworkout.BMIStatus
import br.queiroz.a7minutesworkout.R
import br.queiroz.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String =
        METRIC_UNITS_VIEW // a variable to hold a value to make a selected view visible

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBMIActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI".uppercase()
        }

        binding?.toolbarBMIActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.btnCalculateBMI?.setOnClickListener {
            if (validateMetricUnits()) {
                displayResult()
            } else {
                if (validateUSUnits()) {
                    displayResult()
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
//          with radio buttons, use setOnCheckedListener because this give id which button clicked
        binding?.rgCustomizeUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE

        binding?.etMetricUnitWeight?.text?.clear()
        binding?.etMetricUnitHeight?.text?.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitHeight?.visibility =
            View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.tilMetricUnitWeight?.visibility =
            View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // make weight view visible.
        binding?.tilMetricUsUnitHeightFeet?.visibility =
            View.VISIBLE // make height feet view visible.
        binding?.tilMetricUsUnitHeightInch?.visibility =
            View.VISIBLE // make height inch view visible.

        binding?.etUsMetricUnitWeight?.text!!.clear() // weight value is cleared.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // height feet value is cleared.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // height inch is cleared.

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun calculateUnits(): Float {
        var heightValue = 0f
        var weightValue = 0f
        when (currentVisibleView) {
            METRIC_UNITS_VIEW -> {
                heightValue = binding?.etMetricUnitHeight.let {
                    it?.text.toString().toFloat() / 100
                }
                weightValue = binding?.etMetricUnitWeight.let {
                    it?.text.toString().toFloat()
                }
            }
            US_UNITS_VIEW -> {
                val feetValue = binding?.etUsMetricUnitHeightFeet?.text.toString().toFloat()
                val heightInchValue = binding?.etUsMetricUnitHeightInch.let {
                    it?.text.toString().toFloat()
                }
                heightValue = heightInchValue + feetValue * 12
                weightValue = binding?.etUsMetricUnitWeight.let {
                    it?.text.toString().toFloat()
                }

                return 703 * (weightValue / heightValue.pow(2))
            }
        }

        return weightValue.div(heightValue.pow(2))
    }

    private fun displayResult() {
        val bmi = calculateUnits()
        val bmiLabel: String
        val bmiDescription: String

//        Enum calcule with method in companio object
//      Is different of Udemy
        BMIStatus.getMetricsFromBMI(bmi).apply {
            bmiLabel = label
            bmiDescription = description
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN)
            .toString()

        binding?.tvResultBMICalc.let {
            it?.text = bmiValue
        }
        binding?.tvResultBMIText?.text = bmiLabel
        binding?.tvResultDescription?.text = bmiDescription
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isBlank()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isBlank()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        val isValid: Boolean = when {
            binding?.etUsMetricUnitWeight?.text.toString().isBlank() -> {
                false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isBlank() -> {
                false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isBlank() -> {
                false
            }
            else -> {
                true
            }
        }

        return isValid
    }

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric unit view
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US unit view
    }
}
