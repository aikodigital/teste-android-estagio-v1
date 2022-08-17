package br.com.aj.message.appaiko.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aj.message.appaiko.databinding.SearchLayBinding
import br.com.aj.message.appaiko.ui.adapter.SearchAdapter

class ViewAlertSearch :DialogFragment() {

    interface Onclick {
        fun click(p0:Double,p1:Double)
    }

    lateinit var binding: SearchLayBinding
    lateinit var adapter: SearchAdapter
    lateinit var mOnInputListener: Onclick


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchLayBinding.inflate(inflater, container,false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getParcelableArrayList<SearchAdapter.Item>("items")

        adapter = SearchAdapter()
        adapter.submitListFilterlist(item)
        adapter.submitList(item)

        binding.textInputLayout.editText?.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.list.hasFixedSize()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)


        adapter.clickViewPosition = { d: Double, d1: Double ->

            mOnInputListener.click(d,d1)
            dismiss()

        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }
}