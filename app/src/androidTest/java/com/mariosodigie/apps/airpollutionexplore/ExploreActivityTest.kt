package com.mariosodigie.apps.airpollutionexplore

import android.app.Activity
import android.app.Instrumentation
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import com.mariosodigie.apps.airpollutionexplore.utils.ViewModelRule
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerActivity
import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerViewModel
import com.mariosodigie.apps.airpollutionexplore.explorefeature.PollutionDetailsViewModel
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@RunWith(AndroidJUnit4::class)
class ExploreActivityTest {

    @get:Rule
    val testRule = IntentsTestRule(ExplorerActivity::class.java, true, false)
    @get:Rule
    val viewModelRule = ViewModelRule()

    private val requestInProgress = MutableLiveData<Boolean>()
    private val response = MutableLiveData<String?>()
    private val serviceError = MutableLiveData<ServiceError>()


    @Mock
    lateinit var viewModel: ExplorerViewModel

    @Before
    fun setUp() {
        whenever(viewModel.response).thenReturn(response)
        whenever(viewModel.requestInProgress).thenReturn(requestInProgress)
        whenever(viewModel.serviceError).thenReturn(serviceError)
        testRule.launchActivity(null)
    }

    @Test
    fun showProgressBar() {
        requestInProgress.postValue(true)
        onView(ViewMatchers.withId(R.id.requestProgress))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun showErrorDialog() {
        serviceError.postValue(ServiceError("",""))
        onView(ViewMatchers.withText(CoreMatchers.containsString("Something went wrong"))).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun startsMagicLinkRequest() {
        val pollutionDetailsActivityMatcher =
            ComponentNameMatchers.hasClassName(PollutionDetailsViewModel::class.java.canonicalName)
        Intents.intending(IntentMatchers.hasComponent(pollutionDetailsActivityMatcher))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
        onView(ViewMatchers.withId(R.id.map)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(pollutionDetailsActivityMatcher))
    }

}