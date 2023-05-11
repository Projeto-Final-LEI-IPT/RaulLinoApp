package com.example.raullino.ui.buildingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.raullino.JsonParse
import com.example.raullino.databinding.FragmentBuildingDetailBinding

class BuildingDetailFragment : Fragment() {

    private var _binding: FragmentBuildingDetailBinding? = null

    lateinit var textTitle: TextView
    lateinit var textInfo: TextView
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    val imageUrlList = listOf(
        "https://images.unsplash.com/photo-1621318164984-b06589834c91?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTU3MDZ8MHwxfHJhbmRvbXx8fHx8fHx8fDE2MjM2OTk4MjI&ixlib=rb-1.2.1&q=80&w=1080",
        "https://images.unsplash.com/photo-1621551122354-e96737d64b70?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTU3MDZ8MHwxfHJhbmRvbXx8fHx8fHx8fDE2MjM2OTk4MjI&ixlib=rb-1.2.1&q=80&w=1080",
        "https://images.unsplash.com/photo-1621616875450-79f024a8c42c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTU3MDZ8MHwxfHJhbmRvbXx8fHx8fHx8fDE2MjM2OTk4MjI&ixlib=rb-1.2.1&q=80&w=1080",
        "https://images.unsplash.com/photo-1621687947404-e41b3d139088?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxOTU3MDZ8MHwxfHJhbmRvbXx8fHx8fHx8fDE2MjM2OTk4MjI&ixlib=rb-1.2.1&q=80&w=1080",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTSmH3IM9oI8b7p0TOj0rXQs1l7S4XuUKWBw&usqp=CAU"
    )

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonParse = JsonParse(requireContext())

        textTitle = binding.textTitle
        textInfo = binding.textInfo

        textTitle.text = jsonParse.get_title("3")
        textInfo.text = jsonParse.get_info("3")

        //initializing the adapter
        imageViewPagerAdapter = ImageViewPagerAdapter(imageUrlList)

        setUpViewPager() // Adicionar esta linha

        return root
    }

    private fun setUpViewPager() {

        binding.viewPager.adapter = imageViewPagerAdapter

        //set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        //select any page you want as your starting page
        val currentPageIndex = 0
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    binding.imageNumberTV.text = "${position + 1} / ${imageUrlList.size}"
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        // unregistering the onPageChangedCallback
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }

}