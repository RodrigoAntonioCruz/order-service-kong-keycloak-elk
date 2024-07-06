package com.example.core.ports.output;

import com.example.core.FileOrderLine;

public interface SendOrderOutputPort {
    void send(final FileOrderLine fileOrderLine);
}
