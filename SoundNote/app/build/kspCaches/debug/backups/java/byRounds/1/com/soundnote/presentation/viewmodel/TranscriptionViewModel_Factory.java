package com.soundnote.presentation.viewmodel;

import com.soundnote.domain.repository.RecordingRepository;
import com.soundnote.domain.usecase.SpeechToTextManager;
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
public final class TranscriptionViewModel_Factory implements Factory<TranscriptionViewModel> {
  private final Provider<SpeechToTextManager> speechToTextManagerProvider;

  private final Provider<RecordingRepository> recordingRepositoryProvider;

  public TranscriptionViewModel_Factory(Provider<SpeechToTextManager> speechToTextManagerProvider,
      Provider<RecordingRepository> recordingRepositoryProvider) {
    this.speechToTextManagerProvider = speechToTextManagerProvider;
    this.recordingRepositoryProvider = recordingRepositoryProvider;
  }

  @Override
  public TranscriptionViewModel get() {
    return newInstance(speechToTextManagerProvider.get(), recordingRepositoryProvider.get());
  }

  public static TranscriptionViewModel_Factory create(
      Provider<SpeechToTextManager> speechToTextManagerProvider,
      Provider<RecordingRepository> recordingRepositoryProvider) {
    return new TranscriptionViewModel_Factory(speechToTextManagerProvider, recordingRepositoryProvider);
  }

  public static TranscriptionViewModel newInstance(SpeechToTextManager speechToTextManager,
      RecordingRepository recordingRepository) {
    return new TranscriptionViewModel(speechToTextManager, recordingRepository);
  }
}
