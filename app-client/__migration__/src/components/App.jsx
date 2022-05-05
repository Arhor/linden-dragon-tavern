import React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from '@mui/material/styles';

import AppLayout from '@/components/AppLayout';
import ErrorBoundary from '@/components/ErrorBoundary';
import theme from '@/theme.js';
import { store, StoreContext } from '@/store';

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <ErrorBoundary>
                <StoreContext.Provider value={store}>
                    <BrowserRouter>
                        <AppLayout />
                    </BrowserRouter>
                </StoreContext.Provider>
            </ErrorBoundary>
        </ThemeProvider>
    );
}

export default App;
