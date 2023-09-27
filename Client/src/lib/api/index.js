function getDefaultHeaders(includeContent) {
  const headers = {};
  if (includeContent) {
    headers["Content-type"] = "multipart/form-data";
  } else {
    headers["Content-type"] = "application/json";
  }

  let token = cookieStore.get("jwt_token")?.value;
  // if (token) {
  //   headers["Authorization"] = `Bearer ${cookieStore.get("jwt_token")?.value}`;
  // }

  return headers;
}

async function apiClient(endpoint, options, method, isFormData) {
  try {
    const url = `${process.env.NEXT_PUBLIC_API_URL}${endpoint}`;
    // Add any necessary headers or configurations to the 'options' object
    const requestOptions = {
      headers: {
        ...getDefaultHeaders(false),
      },
      formDataHeader: {
        ...getDefaultHeaders(true),
      },
    };

    const response =
      method === "GET" || method === "DELETE"
        ? await fetch(url, { headers: requestOptions.headers, method: method })
        : isFormData
        ? await fetch(url, {
            headers: requestOptions.formDataHeader,
            method: method,
            body: options,
          })
        : await fetch(url, {
            headers: requestOptions.headers,
            method: method,
            body: JSON.stringify(options),
          });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(
        error.errorResponse || "An error occurred during the API request"
      );
    }
    if (method === "DELETE") {
      return await response.text();
    }
    const result = await response.json();
    console.log(result);
    return result.successResponse;
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
    return await apiClient(endpoint, options, "GET", false);
  }

  async postFormAsync(endpoint, options = {}) {
    return await apiClient(endpoint, options, "POST", true);
  }

  async deleteAsync(endpoint, options = {}) {
    return await apiClient(endpoint, options, "DELETE", false);
  }

  async postAsync(endpoint, options = {}) {
    return await apiClient(endpoint, options, "POST", false);
  }

  async putAsync(endpoint, options = {}) {
    return await apiClient(endpoint, options, "PUT", false);
  }
}

export default new ApiClient();
