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
public final class DeleteRecordingUseCase_Factory implements Factory<DeleteRecordingUseCase> {
  private final Provider<RecordingRepository> repositoryProvider;

  public DeleteRecordingUseCase_Factory(Provider<RecordingRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteRecordingUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteRecordingUseCase_Factory create(
      Provider<RecordingRepository> repositoryProvider) {
    return new DeleteRecordingUseCase_Factory(repositoryProvider);
  }

  public static DeleteRecordingUseCase newInstance(RecordingRepository repository) {
    return new DeleteRecordingUseCase(repository);
  }
}
