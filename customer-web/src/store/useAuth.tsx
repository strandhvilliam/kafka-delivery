import * as React from "react";
// import { useLogin } from "../api/mutations/useLogin";

export type AuthToken = {
  accessToken: string;
  refreshToken: string;
};

export type LoginData = {
  username: string;
  password: string;
};

export type Identity = {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
};

export interface AuthContext {
  isAuthenticated: boolean;
  login: (data: LoginData) => void;
  logout: () => void;
  user: Identity | null;
  authToken: AuthToken | null;
}

const AuthContext = React.createContext<AuthContext | null>(null);

const userKey = "auth.user";
const tokenKey = "auth.token";

function getStoredUser(): Identity | null {
  const stored = localStorage.getItem(userKey);
  if (stored) {
    return JSON.parse(stored) as Identity;
  }
  return null;
}

function getStoredAuthToken(): AuthToken | null {
  const stored = localStorage.getItem(tokenKey);
  if (stored) {
    return JSON.parse(stored) as AuthToken;
  }
  return null;
}

function setStoredUser(user: Identity | null) {
  if (user) {
    localStorage.setItem(userKey, JSON.stringify(user));
  } else {
    localStorage.removeItem(userKey);
  }
}

function setStoredAuthToken(token: AuthToken | null) {
  if (token) {
    localStorage.setItem(tokenKey, JSON.stringify(token));
  } else {
    localStorage.removeItem(tokenKey);
  }
}

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = React.useState<Identity | null>(getStoredUser());
  const [authToken, setAuthToken] = React.useState<AuthToken | null>(
    getStoredAuthToken(),
  );
  const isAuthenticated = !!user;

  const logout = React.useCallback(() => {
    setStoredUser(null);
    setUser(null);
    setStoredAuthToken(null);
    setAuthToken(null);
  }, []);

  const login = React.useCallback(({ username, password }: LoginData) => {
    if (!username || !password) {
      throw new Error("Invalid username or password");
    }
    // mutateAsync({ username, password })
    //   .then((data) => {
    //     if (isSuccess) {
    //       setStoredUser(data.identity);
    //       setUser(data.identity);
    //       setAuthToken(data.tokens);
    //       setStoredAuthToken(data.tokens);
    //       //TODO: sonner success
    //     } else if (isError) {
    //       //TODO: sonner error
    //     }
    //   })
    //   .catch(() => {
    //     // TODO: sonner error
    //   });
    //

    setAuthToken({
      accessToken: "123",
      refreshToken: "456",
    });
    setStoredAuthToken({
      accessToken: "123",
      refreshToken: "456",
    });
    setStoredUser({
      id: "1",
      username: "test123",
      firstName: "John",
      lastName: "Doe",
      email: "test@test.com",
      roles: ["admin"],
    });
    setUser({
      id: "1",
      username: "test123",
      firstName: "John",
      lastName: "Doe",
      email: "test@test.com",
      roles: ["admin"],
    });
  }, []);

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, authToken, user, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = React.useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
