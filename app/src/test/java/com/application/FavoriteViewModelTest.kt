
import com.application.weather.database.FavoriteEntity
import com.application.weather.database.WeatherDatabase
import com.application.weather.network.WeatherService
import com.application.weather.repository.WeatherRepository
import com.application.weather.ui.FavoriteViewModel
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class FavoriteViewModelTest : FunSpec({

    val database = mockk<WeatherDatabase>()

    val weatherService = mockk<WeatherService>()

    val repository: WeatherRepository by lazy {
        WeatherRepository(database, weatherService)
    }

    val viewModel: FavoriteViewModel by lazy {
        FavoriteViewModel(repository)
    }

    beforeTest {
        MockKAnnotations.init(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    test("view model get favorite from database") {
        val favorite = BehaviorSubject.create<List<FavoriteEntity>>()
        every { repository.getFavourite() } returns favorite
        viewModel.getFavorite()
        verify { repository.getFavourite()}
    }


})