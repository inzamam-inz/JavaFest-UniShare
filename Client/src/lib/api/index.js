import { cookies } from "next/headers";

function getDefaultHeaders(includeContent) {
  const cookieStore = cookies();
  const headers = {
    "Client-Type": "Web",
  };
  if (includeContent) {
    headers["Content-type"] = "application/json";
  }

  let token = cookieStore.get("jwt_token")?.value;
  if (token) {
    headers["Authorization"] = `Bearer ${cookieStore.get("jwt_token")?.value}`;
  }

  return headers;
}

async function apiClient(endpoint, options = {}) {
  try {
    const url = `${process.env.NEXT_PUBLIC_API_URL}/${endpoint}`;

    // Add any necessary headers or configurations to the 'options' object
    const requestOptions = {
      ...options,
      headers: {
        ...getDefaultHeaders(
          options.body && options.body instanceof FormData ? false : true
        ),
        ...options.headers,
      },
    };

    // Execute the API request
    const response = await fetch(url, requestOptions);

    if (!response.ok) {
      throw new Error(
        (await response.text()) || "An error occurred during the API request"
      );
    }

    const data = await response.json();

    return data;
  } catch (error) {
    throw error;
  }
}

// class

class ApiClient {
  constructor() {
    this.getAsync = this.getAsync.bind(this);
    this.deleteAsync = this.deleteAsync.bind(this);
    this.postAsync = this.postAsync.bind(this);
    this.putAsync = this.putAsync.bind(this);
  }

  async getAsync(endpoint, options = {}) {
    return await apiClient(endpoint, {
      ...options,
      method: "GET",
    });
  }

  async deleteAsync(endpoint, options = {}) {
    return await apiClient(endpoint, {
      ...options,
      method: "DELETE",
    });
  }

  async postAsync(endpoint, options = {}) {
    return await apiClient(endpoint, {
      ...options,
      method: "POST",
    });
  }

  async putAsync(endpoint, options = {}) {
    return await apiClient(endpoint, {
      ...options,
      method: "PUT",
    });
  }
}

export default new ApiClient();
