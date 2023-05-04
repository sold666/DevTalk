import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_talk.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.databinding.FragmentProfileEditBinding
import com.dev_talk.main.profile.edit.EditProfileChatsAdapter
import com.dev_talk.main.profile.edit.EditProfileLinkAdapter
import com.dev_talk.main.profile.information.ProfileInformationChatsAdapter
import com.dev_talk.main.structures.*

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var data: List<ProfileData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            data = getProfileData()
            setUpChats(recyclerView = myChats)
            setUpLinks(socialNetwork)

            saveAllBtn.setOnClickListener{
                findNavController().navigate(R.id.action_profileEditFragment_to_profileInformationFragment)
            }
        }
    }

    private fun setUpChats(recyclerView: RecyclerView) {
        val manager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (data[position]) {
                    is Header -> 2
                    is Item -> 1
                }
            }
        }
        manager.orientation = RecyclerView.VERTICAL
        recyclerView.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = manager
            adapter = EditProfileChatsAdapter(data)
        }
    }

    private fun setUpLinks(recyclerView: RecyclerView) {
        val manager = object : LinearLayoutManager(context) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        manager.orientation = RecyclerView.HORIZONTAL
        recyclerView.apply {
            layoutManager = manager
            adapter = EditProfileLinkAdapter(getLinks())
            setHasFixedSize(true)
        }
    }

    private fun getLinks() : ArrayList<Link> {
        return arrayListOf(
            Link(R.drawable.ic_person),
            Link(R.drawable.ic_add_new_chat_btn)
        )
    }

    private fun getProfileData(): ArrayList<ProfileData> {
        return arrayListOf(
            Header("Profession №1"),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "C++"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Java"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "C"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Kotlin"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "F"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Ruby"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Go"
                )
            ),
            Header("Profession №2"),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Css"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Html"
                )
            ),
            Header("Profession №3"),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Selenide"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Selenium"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Java"
                )
            )
        )
    }
}
