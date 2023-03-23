
import com.application.weather.data.WeatherData
import com.application.weather.database.WeatherDatabase
import com.application.weather.database.WeatherEntity
import com.application.weather.network.WeatherService
import com.application.weather.repository.WeatherRepository
import io.kotest.core.spec.style.FunSpec
import io.mockk.*
import io.reactivex.subjects.BehaviorSubject

class WeatherRepositoryTest : FunSpec({

    val database = mockk<WeatherDatabase>()

    val weatherService = mockk<WeatherService>()

    val repository: WeatherRepository by lazy {
        WeatherRepository(database, weatherService)
    }

    beforeTest {
        MockKAnnotations.init(this)
    }

    test("get weather from weather service") {
        val weather = BehaviorSubject.create<WeatherData>()
        every { weatherService.getWeather("")} returns weather
        repository.getWeather("")
        verify { weatherService.getWeather("") }
    }

    test("get weather from database") {
        val weather = BehaviorSubject.create<List<WeatherEntity>>()
        every { database.getWeather()} returns weather
        repository.getWeather()
        verify { database.getWeather() }
    }

})