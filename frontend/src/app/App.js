import React from "react";
import AppRouter from "./AppRouter/AppRouter";
import { RootContextProvider } from "./RootContext";

const App = () => (
  <RootContextProvider>
    <AppRouter />
  </RootContextProvider>
);

export default App;
