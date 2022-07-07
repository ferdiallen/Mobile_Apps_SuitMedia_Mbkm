package com.example.mobileappssuitmedia.mediator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileappssuitmedia.data.dto.PageData
import com.example.mobileappssuitmedia.databinding.ListItemsHolderBinding
import com.example.mobileappssuitmedia.local.LocalUserEntity

class RecycleAdapter(private val listener: (String) -> Unit) :
    PagingDataAdapter<LocalUserEntity, RecycleAdapter.MainRecyclerHolder>(DIIF_CALLBACK) {
    inner class MainRecyclerHolder(binding: ListItemsHolderBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        val images = binding.userImage
        val firstName = binding.userFirstName
        val lastName = binding.userLastName
        val email = binding.userAddress
        override fun onClick(p0: View?) {
            if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                listener.invoke(
                    "${getItem(position = absoluteAdapterPosition)?.firstName} ${
                        getItem(
                            position = absoluteAdapterPosition
                        )?.lastName
                    }"
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerHolder {
        return MainRecyclerHolder(
            ListItemsHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MainRecyclerHolder, position: Int) {
        val currentData = getItem(position)
        currentData?.let {
            holder.firstName.text = it.firstName
            holder.lastName.text = it.lastName
            Glide.with(holder.itemView.context).load(it.avatar).into(holder.images)
            holder.email.text = it.email
        }
    }

    private companion object {
        val DIIF_CALLBACK = object : DiffUtil.ItemCallback<LocalUserEntity>() {
            override fun areItemsTheSame(
                oldItem: LocalUserEntity,
                newItem: LocalUserEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: LocalUserEntity,
                newItem: LocalUserEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}