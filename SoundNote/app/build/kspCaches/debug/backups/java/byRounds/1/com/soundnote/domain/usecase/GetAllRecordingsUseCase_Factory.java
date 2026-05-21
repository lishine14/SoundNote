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
public final class GetAllRecordingsUseCase_Factory implements Factory<GetAllRecordingsUseCase> {
  private final Provider<RecordingRepository> repositoryProvider;

  public GetAllRecordingsUseCase_Factory(Provider<RecordingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllRecordingsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllRecordingsUseCase_Factory create(
      Provider<RecordingRepository> repositoryProvider) {
    return new GetAllRecordingsUseCase_Factory(repositoryProvider);
  }

  public static GetAllRecordingsUseCase newInstance(RecordingRepository repository) {
    return new GetAllRecordingsUseCase(repository);
  }
}
