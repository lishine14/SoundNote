package com.soundnote.domain.usecase;

import com.soundnote.domain.repository.RecordingRepository;
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
public final class SaveRecordingUseCase_Factory implements Factory<SaveRecordingUseCase> {
  private final Provider<RecordingRepository> repositoryProvider;

  public SaveRecordingUseCase_Factory(Provider<RecordingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveRecordingUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveRecordingUseCase_Factory create(
      Provider<RecordingRepository> repositoryProvider) {
    return new SaveRecordingUseCase_Factory(repositoryProvider);
  }

  public static SaveRecordingUseCase newInstance(RecordingRepository repository) {
    return new SaveRecordingUseCase(repository);
  }
}
