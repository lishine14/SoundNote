package com.soundnote.presentation.viewmodel;

import com.soundnote.domain.repository.TagRepository;
import com.soundnote.domain.usecase.DeleteRecordingUseCase;
import com.soundnote.domain.usecase.GetAllRecordingsUseCase;
import com.soundnote.domain.usecase.SearchRecordingsUseCase;
import com.soundnote.domain.usecase.ToggleFavoriteUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class LibraryViewModel_Factory implements Factory<LibraryViewModel> {
  private final Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider;

  private final Provider<SearchRecordingsUseCase> searchRecordingsUseCaseProvider;

  private final Provider<DeleteRecordingUseCase> deleteRecordingUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  private final Provider<TagRepository> tagRepositoryProvider;

  public LibraryViewModel_Factory(Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider,
      Provider<SearchRecordingsUseCase> searchRecordingsUseCaseProvider,
      Provider<DeleteRecordingUseCase> deleteRecordingUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    this.getAllRecordingsUseCaseProvider = getAllRecordingsUseCaseProvider;
    this.searchRecordingsUseCaseProvider = searchRecordingsUseCaseProvider;
    this.deleteRecordingUseCaseProvider = deleteRecordingUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(getAllRecordingsUseCaseProvider.get(), searchRecordingsUseCaseProvider.get(), deleteRecordingUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get(), tagRepositoryProvider.get());
  }

  public static LibraryViewModel_Factory create(
      Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider,
      Provider<SearchRecordingsUseCase> searchRecordingsUseCaseProvider,
      Provider<DeleteRecordingUseCase> deleteRecordingUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    return new LibraryViewModel_Factory(getAllRecordingsUseCaseProvider, searchRecordingsUseCaseProvider, deleteRecordingUseCaseProvider, toggleFavoriteUseCaseProvider, tagRepositoryProvider);
  }

  public static LibraryViewModel newInstance(GetAllRecordingsUseCase getAllRecordingsUseCase,
      SearchRecordingsUseCase searchRecordingsUseCase,
      DeleteRecordingUseCase deleteRecordingUseCase, ToggleFavoriteUseCase toggleFavoriteUseCase,
      TagRepository tagRepository) {
    return new LibraryViewModel(getAllRecordingsUseCase, searchRecordingsUseCase, deleteRecordingUseCase, toggleFavoriteUseCase, tagRepository);
  }
}
