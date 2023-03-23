
import com.application.weather.database.WeatherDatabase
import com.application.weather.database.WeatherEntity
import com.application.weather.network.WeatherService
import com.application.weather.repository.WeatherRepository
import com.application.weather.ui.RecentViewModel
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RecentViewModelTest : FunSpec({

    val database = mockk<WeatherDatabase>()

    val weatherService = mockk<WeatherService>()

    val repository: WeatherRepository by lazy {
        WeatherRepository(database, weatherService)
    }

    val viewModel: RecentViewModel by lazy {
        RecentViewModel(repository)
    }

    beforeTest {
        MockKAnnotations.init(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    test("view model get weather from database") {
        val weather = BehaviorSubject.create<List<WeatherEntity>>()
        every { repository.getWeather() } returns weather
        viewModel.getWeather()
        verify { repository.getWeather()}
    }

})