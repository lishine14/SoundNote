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
public final class ToggleFavoriteUseCase_Factory implements Factory<ToggleFavoriteUseCase> {
  private final Provider<RecordingRepository> repositoryProvider;

  public ToggleFavoriteUseCase_Factory(Provider<RecordingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleFavoriteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleFavoriteUseCase_Factory create(
      Provider<RecordingRepository> repositoryProvider) {
    return new ToggleFavoriteUseCase_Factory(repositoryProvider);
  }

  public static ToggleFavoriteUseCase newInstance(RecordingRepository repository) {
    return new ToggleFavoriteUseCase(repository);
  }
}
