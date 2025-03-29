package net.aurika.auspice.configs.messages.context;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

public interface CascadingMessageContextProvider extends MessageContextProvider {

  /**
   * Adds edits to the provided {@linkplain MessageContext}.
   *
   * @param messageContext the context to add edits
   */
  @MustBeInvokedByOverriders
  void addMessageContextEdits(@NotNull MessageContext messageContext);

  @Override
  default @NotNull MessageContext messageContext() {
    MessageContext newMsgCtx = MessageContext.messageContext();
    this.addMessageContextEdits(newMsgCtx);
    return newMsgCtx;
  }

}
