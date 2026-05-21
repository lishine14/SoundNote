package com.soundnote.domain.usecase;

import android.content.Context;
import com.soundnote.domain.repository.RecordingRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SpeechToTextManager_Factory implements Factory<SpeechToTextManager> {
  private final Provider<Context> contextProvider;

  private final Provider<RecordingRepository> recordingRepositoryProvider;

  public SpeechToTextManager_Factory(Provider<Context> contextProvider,
      Provider<RecordingRepository> recordingRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.recordingRepositoryProvider = recordingRepositoryProvider;
  }

  @Override
  public SpeechToTextManager get() {
    return newInstance(contextProvider.get(), recordingRepositoryProvider.get());
  }

  public static SpeechToTextManager_Factory create(Provider<Context> contextProvider,
      Provider<RecordingRepository> recordingRepositoryProvider) {
    return new SpeechToTextManager_Factory(contextProvider, recordingRepositoryProvider);
  }

  public static SpeechToTextManager newInstance(Context context,
      RecordingRepository recordingRepository) {
    return new SpeechToTextManager(context, recordingRepository);
  }
}
