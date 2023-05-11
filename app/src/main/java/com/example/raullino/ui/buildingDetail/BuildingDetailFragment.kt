package com.example.raullino.ui.buildingDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.raullino.JsonParse
import com.example.raullino.R
import com.example.raullino.databinding.FragmentBuildingDetailBinding

class BuildingDetailFragment : Fragment() {

    private var _binding: FragmentBuildingDetailBinding? = null
    lateinit var textTitle: TextView
    lateinit var textInfo: TextView
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    val imageDrawableList = ArrayList<Int>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Id do Edificio
        var buildingId  = "4"

        _binding = FragmentBuildingDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonParse = JsonParse(requireContext())

        textTitle = binding.textTitle
        textInfo = binding.textInfo
        textTitle.text = jsonParse.get_title(buildingId)
        textInfo.text = jsonParse.get_info(buildingId)

        var imagesArray = jsonParse.get_image(buildingId)

        for(image in imagesArray){
            var imagePath = image.split("/").toTypedArray()  //array do path da imagem
            // tamanho do array
            var Arraynum = imagePath.size
            // dividir o nome da imagem em 2 (Ex:['sam_7432','jpg'])
            var image = imagePath[Arraynum-1].split(".").toTypedArray()
            // tratamento do nome da imagem
            var imageName = image[0].toLowerCase().replace("-","_").replace(" ","_").replace("(","_").replace(")","_")
            val drawableName = imageName
            val packageName = requireContext().packageName
            val resourceId = requireContext().resources.getIdentifier(drawableName, "drawable", packageName)
            imageDrawableList.add(resourceId)
        }

        // initializing the adapter
        imageViewPagerAdapter = ImageViewPagerAdapter(imageDrawableList)

        setUpViewPager()

        return root
    }

    private fun setUpViewPager() {
        binding.viewPager.adapter = imageViewPagerAdapter
        // set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // select any page you want as your starting page
        val currentPageIndex = 0
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //update the image number textview
                    binding.buildingImage.text = "${position + 1} / ${imageDrawableList.size}"
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