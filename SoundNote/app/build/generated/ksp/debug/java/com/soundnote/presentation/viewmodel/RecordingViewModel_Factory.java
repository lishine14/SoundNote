package com.soundnote.presentation.viewmodel;

import android.app.Application;
import com.soundnote.data.preferences.PreferencesManager;
import com.soundnote.domain.usecase.GetAllRecordingsUseCase;
import com.soundnote.domain.usecase.SaveRecordingUseCase;
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
public final class RecordingViewModel_Factory implements Factory<RecordingViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<SaveRecordingUseCase> saveRecordingUseCaseProvider;

  private final Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public RecordingViewModel_Factory(Provider<Application> applicationProvider,
      Provider<SaveRecordingUseCase> saveRecordingUseCaseProvider,
      Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.applicationProvider = applicationProvider;
    this.saveRecordingUseCaseProvider = saveRecordingUseCaseProvider;
    this.getAllRecordingsUseCaseProvider = getAllRecordingsUseCaseProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public RecordingViewModel get() {
    return newInstance(applicationProvider.get(), saveRecordingUseCaseProvider.get(), getAllRecordingsUseCaseProvider.get(), preferencesManagerProvider.get());
  }

  public static RecordingViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<SaveRecordingUseCase> saveRecordingUseCaseProvider,
      Provider<GetAllRecordingsUseCase> getAllRecordingsUseCaseProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new RecordingViewModel_Factory(applicationProvider, saveRecordingUseCaseProvider, getAllRecordingsUseCaseProvider, preferencesManagerProvider);
  }

  public static RecordingViewModel newInstance(Application application,
      SaveRecordingUseCase saveRecordingUseCase, GetAllRecordingsUseCase getAllRecordingsUseCase,
      PreferencesManager preferencesManager) {
    return new RecordingViewModel(application, saveRecordingUseCase, getAllRecordingsUseCase, preferencesManager);
  }
}
