# Top 100 Albums

This project is an Android application that displays the top 100 albums. The app fetches data from an API and saves it in a local database using Realm, allowing for an offline-first experience. Users can retry fetching data if the initial network request fails.

## Demo
Please refer to the demo by clicking [Here](https://drive.google.com/file/d/1H1WJJZaB1alht9HeZR600vF8ABovFrOl/view?usp=sharing)

## Features

- Display a list of the top 100 albums.
- Fetch albums from a local Realm database first, then append with results from the API.
- Retry fetching data if the initial network request fails.
- Offline-first approach ensures that data is available even without an internet connection.

## Screens

1. **Albums Screen**: Displays a list of the top 100 albums.
   - Shows a loading indicator while fetching data.
   - Displays an error message with a retry button if the initial network request fails.
   - Fetches data from the local Realm database if available.

2. **Album Details Screen**: Displays detailed information about a selected album.
   - Shows the album's artwork, title, and other relevant information.
   - Provides a button to visit the album's URL.

## Dependencies

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt for Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Realm for Android](https://realm.io/products/realm-database/)
- [Retrofit for networking](https://square.github.io/retrofit/)

## Code Snippets
### UI
#### MainActivity
        class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                SongsComposeTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        AppNavHost()
                    }
                }
            }
        }
    } 
    
#### AlbumsScreen
    @Composable
    fun AlbumsScreen(
        viewModel: AlbumsViewModel = hiltViewModel(),
        onAlbumClick: (String, String) -> Unit
    ) {
        val uiState by viewModel.uiState.collectAsState()
    
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Top 100 Albums", style = MaterialTheme.typography.headlineMedium) },
                    actions = {
                        if (uiState.error != null && uiState.albums.isEmpty()) {
                            IconButton(onClick = { viewModel.retryFetch() }) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Retry",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center // Center the content within the Box
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }
                    uiState.error != null && uiState.albums.isEmpty() -> {
                        Text(
                            text = "Failed to load albums. Please try again.",
                            color = Color.Red,
                            textAlign = TextAlign.Center // Center the text within the Text composable
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(uiState.albums) { album ->
                                AlbumItem(album = album, onAlbumClick = onAlbumClick)
                            }
                        }
                    }
                }
            }
        }
    }

#### AlbumDetailsScreen
    @Composable
    fun AlbumDetailsScreen(
        albumId: String,
        copyright: String,
        viewModel: AlbumDetailsViewModel = hiltViewModel(),
        navController: NavController
    ) {
        val uiState by viewModel.uiState.collectAsState()
    
        LaunchedEffect(Unit) {
            viewModel.getAlbumDetails(albumId)
        }
    
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Failed to load album details. Please try again.", color = Color.Red)
                }
            }
            uiState.album != null -> {
                val album = uiState.album
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = album.artworkUrl100,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(top = 16.dp)
                        )
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(top = 32.dp, start = 16.dp)
                                .background(color = Color.Gray.copy(alpha = 0.6f), shape = CircleShape)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = album.name, style = MaterialTheme.typography.headlineMedium)
                    Text(text = album.artistName, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    GenresList(genres = album.genres)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = formatDate(album.releaseDate), style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = copyright, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* TODO: Navigate to album URL */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Visit Album")
                    }
                }
            }
        }
    }

### Repository
    interface AlbumsRepository {
        suspend fun getAlbums(count: Int): Flow<Result<RealmFeed>>
        suspend fun getAlbumById(albumId: String): Result<RealmAlbum>
    }
    
    @Singleton
    class AlbumsRepositoryImpl @Inject constructor(
        private val albumsApi: AlbumsApi,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : AlbumsRepository {

    override suspend fun getAlbums(count: Int): Flow<Result<RealmFeed>> {
        return flow {
            val localFeed = getLocalFeed()
            if (localFeed?.albums?.isNotEmpty() == true) {
                emit(Result.success(localFeed))
            }
            try {
                val response = withContext(coroutineDispatcher) {
                    albumsApi.getTopSongs(count = count)
                }
                val realmFeed = response.feed.toRealmFeed()
                saveFeedToLocal(realmFeed)
                emit(Result.success(realmFeed))
            } catch (e: Exception) {
                localFeed?.let { feed ->
                    if (feed.albums?.isNotEmpty() == true){
                        emit(Result.success(feed))
                    } else{
                        emit(Result.failure(e))
                    }
                }
            }
        }
    }

    override suspend fun getAlbumById(albumId: String): Result<RealmAlbum> {
        return withContext(coroutineDispatcher) {
            val album = getLocalAlbumById(albumId)
            if (album != null) {
                Result.success(album)
            } else {
                Result.failure(Exception("Album not found"))
            }
        }
    }
    }

### ViewModels

#### AlbumsViewModel
    @HiltViewModel
    class AlbumsViewModel @Inject constructor(
        private val repository: AlbumsRepository
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumUiState(isLoading = true))
    val uiState: StateFlow<AlbumUiState> = _uiState

    init {
        getTopAlbums()
    }

    private fun getTopAlbums() {
        viewModelScope.launch {
            repository
                .getAlbums(100)
                .collect { result ->
                    result.onSuccess { feed ->
                        _uiState.update { it.copy(isLoading = false, albums = feed.albums) }
                    }.onFailure { error ->
                        _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                    }
                }
        }
    }

    fun retryFetch() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        getTopAlbums()
    }
    }

### AlbumDetailsViewModel
    @HiltViewModel
    class AlbumDetailsViewModel @Inject constructor(
        private val repository: AlbumsRepository
    ) : ViewModel() {
    
        private val _uiState = MutableStateFlow(AlbumDetailsUiState(isLoading = true))
        val uiState: StateFlow<AlbumDetailsUiState> = _uiState
    
        fun getAlbumDetails(albumId: String) {
            viewModelScope.launch {
                repository.getAlbumById(albumId)
                    .onSuccess { album ->
                        _uiState.update { it.copy(isLoading = false, album = album.toAlbumUiModel()) }
                    }.onFailure { error ->
                        _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                    }
            }
        }
    }

## Getting Started

### Prerequisites

- Android Studio Bumblebee or later
- Gradle 7.0 or later

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/hussein-yassine/Albums-App
   cd Albums-App
2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the project on an emulator or a physical device.


### Project Structure
- data: Contains data models, repositories, and API definitions.
- ui: Contains Composables for the Albums and Album Details screens.
- viewmodel: Contains ViewModel classes to manage UI-related data.

#### API
The app fetches data from the following API:
https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json

#### Local Database
The app uses Realm as the local database to store albums data for offline access.

#### Usage
- Launch the app to view the list of top 100 albums.
- Tap on an album to view its details.
- If the data fetch fails initially, tap the retry button in the app bar to try fetching the data again.

# License
This project is licensed under the GPL License - see the LICENSE file for details.

# Acknowledgements
Thanks to the creators of Jetpack Compose, Hilt, Retrofit, and Realm for their excellent libraries.
