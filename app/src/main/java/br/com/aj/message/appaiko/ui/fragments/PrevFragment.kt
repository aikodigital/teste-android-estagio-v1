package br.com.aj.message.appaiko.ui.fragments


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aj.message.appaiko.data.L
import br.com.aj.message.appaiko.data.V
import br.com.aj.message.appaiko.databinding.LayRecyBinding
import br.com.aj.message.appaiko.ui.adapter.PrevAdapterItem
import br.com.aj.message.appaiko.viewmodel.PrevFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PrevFragment : DialogFragment() {

    private lateinit var binding: LayRecyBinding
    lateinit var adapter: PrevAdapterItem


    val model by sharedViewModel<PrevFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayRecyBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefixo = arguments?.getString("prefixo")


        prefixo?.let { model.getprevBusParada(it) }

        adapter = PrevAdapterItem()

        binding.fechar.setOnClickListener { dismiss() }

        model.prevParadas.observe(viewLifecycleOwner) {
            binding.nomep.text = it.p?.np
            val array = arrayListOf<PrevAdapterItem.ViewsV>()

            it.p?.l?.forEach { l ->

                l.apply {
                    array.add(
                        PrevAdapterItem.ViewsV(
                            2, null, L(
                                 c, cl, lt0, lt1, qv, sl, fid
                            )
                        )
                    )
                }
                l.vs?.forEach { v ->
                    v.apply {
                        array.add(PrevAdapterItem.ViewsV(1, V( a, t, p, px, py, ta, fid), null))
                    }

                }


            }

            adapter.submitList(array)
        }

        binding.recy.adapter = adapter
        binding.recy.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.recy.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }


}