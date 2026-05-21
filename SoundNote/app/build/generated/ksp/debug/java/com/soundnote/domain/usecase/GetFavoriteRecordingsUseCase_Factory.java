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
public final class GetFavoriteRecordingsUseCase_Factory implements Factory<GetFavoriteRecordingsUseCase> {
  private final Provider<RecordingRepository> repositoryProvider;

  public GetFavoriteRecordingsUseCase_Factory(Provider<RecordingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFavoriteRecordingsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFavoriteRecordingsUseCase_Factory create(
      Provider<RecordingRepository> repositoryProvider) {
    return new GetFavoriteRecordingsUseCase_Factory(repositoryProvider);
  }

  public static GetFavoriteRecordingsUseCase newInstance(RecordingRepository repository) {
    return new GetFavoriteRecordingsUseCase(repository);
  }
}
