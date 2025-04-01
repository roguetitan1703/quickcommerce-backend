document.addEventListener("DOMContentLoaded", function () {
  // Initialize syntax highlighting
  hljs.highlightAll();

  // Setup dark mode toggle
  setupDarkMode();

  // Setup search functionality
  setupSearch();

  // Setup dropdown toggles
  setupDropdowns();

  // Setup scrollspy (highlight active section in sidebar)
  setupScrollspy();
});

// Dark mode toggle
function setupDarkMode() {
  const themeToggle = document.querySelector(".theme-toggle");
  const moonIcon = themeToggle.querySelector("i");

  // Check for saved theme preference
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme === "dark") {
    document.body.classList.add("dark-theme");
    moonIcon.classList.remove("fa-moon");
    moonIcon.classList.add("fa-sun");
  }

  themeToggle.addEventListener("click", function () {
    document.body.classList.toggle("dark-theme");

    if (document.body.classList.contains("dark-theme")) {
      localStorage.setItem("theme", "dark");
      moonIcon.classList.remove("fa-moon");
      moonIcon.classList.add("fa-sun");
    } else {
      localStorage.setItem("theme", "light");
      moonIcon.classList.remove("fa-sun");
      moonIcon.classList.add("fa-moon");
    }

    // Re-initialize syntax highlighting when theme changes
    hljs.highlightAll();
  });
}

// Search functionality
function setupSearch() {
  const searchInput = document.getElementById("searchInput");

  searchInput.addEventListener("input", function () {
    const searchTerm = searchInput.value.toLowerCase();

    if (searchTerm.length < 2) {
      // Reset all sections and endpoints
      document.querySelectorAll("section, .endpoint").forEach((el) => {
        el.style.display = "block";
      });
      return;
    }

    // Hide all sections initially
    document.querySelectorAll("section").forEach((section) => {
      section.style.display = "none";
    });

    // Show sections containing the search term
    document
      .querySelectorAll(
        "section h2, section h3, .endpoint-header h3, .endpoint-content p"
      )
      .forEach((el) => {
        if (el.textContent.toLowerCase().includes(searchTerm)) {
          // Show this section/endpoint and its parent section
          let current = el;
          while (current && !current.matches("section")) {
            if (current.classList && current.classList.contains("endpoint")) {
              current.style.display = "block";
            }
            current = current.parentElement;
          }

          if (current && current.matches("section")) {
            current.style.display = "block";
          }
        }
      });
  });
}

// Dropdown toggles for sidebar
function setupDropdowns() {
  const dropdownToggles = document.querySelectorAll(".dropdown-toggle");

  dropdownToggles.forEach((toggle) => {
    toggle.addEventListener("click", function (e) {
      e.preventDefault();
      this.classList.toggle("active");

      // Find next sibling which is the submenu
      const subMenu = this.nextElementSibling;
      if (subMenu && subMenu.classList.contains("sub-menu")) {
        if (this.classList.contains("active")) {
          subMenu.style.maxHeight = subMenu.scrollHeight + "px";
        } else {
          subMenu.style.maxHeight = "0";
        }
      }
    });
  });

  // Open dropdown for active section
  const currentPath = window.location.hash || "#introduction";
  document.querySelectorAll(".nav-links a").forEach((link) => {
    if (link.getAttribute("href") === currentPath) {
      link.classList.add("active");
      const parentLi = link.closest("li");
      if (parentLi) {
        const parentToggle = parentLi.querySelector(".dropdown-toggle");
        if (parentToggle) {
          parentToggle.classList.add("active");
          const subMenu = parentToggle.nextElementSibling;
          if (subMenu) {
            subMenu.style.maxHeight = subMenu.scrollHeight + "px";
          }
        }
      }
    }
  });
}

// Scrollspy functionality
function setupScrollspy() {
  window.addEventListener("scroll", function () {
    const scrollPosition = window.scrollY;

    // Get all sections
    const sections = document.querySelectorAll("section");

    // Find the current section
    let currentSection = null;
    sections.forEach((section) => {
      const sectionTop = section.offsetTop - 100;
      const sectionBottom = sectionTop + section.offsetHeight;

      if (scrollPosition >= sectionTop && scrollPosition < sectionBottom) {
        currentSection = section;
      }
    });

    if (currentSection) {
      const id = currentSection.getAttribute("id");

      // Remove 'active' class from all links
      document.querySelectorAll(".nav-links a").forEach((link) => {
        link.classList.remove("active");
      });

      // Add 'active' class to current link
      const currentLink = document.querySelector(`.nav-links a[href="#${id}"]`);
      if (currentLink) {
        currentLink.classList.add("active");

        // If the link is in a submenu, also activate the parent toggle
        const parentLi = currentLink.closest("li").parentElement.closest("li");
        if (parentLi) {
          const parentToggle = parentLi.querySelector(".dropdown-toggle");
          if (parentToggle) {
            parentToggle.classList.add("active");
            const subMenu = parentToggle.nextElementSibling;
            if (subMenu) {
              subMenu.style.maxHeight = subMenu.scrollHeight + "px";
            }
          }
        }
      }
    }
  });
}

// Show/hide "Try It" forms
function showTryIt(endpoint) {
  const formElement = document.getElementById(`try-it-${endpoint}`);
  if (formElement) {
    if (formElement.style.display === "none") {
      formElement.style.display = "block";
    } else {
      formElement.style.display = "none";
    }
  }
}

// Execute API request from "Try It" form
function executeRequest(endpoint) {
  const baseUrl = "http://localhost:8080/api";
  let url, method, body;

  // Get saved token from localStorage
  const token = localStorage.getItem("api_token");

  // Set up the request based on the endpoint
  switch (endpoint) {
    case "login":
      url = `${baseUrl}/auth/login`;
      method = "POST";
      body = JSON.stringify({
        username: document.getElementById("login-username").value,
        password: document.getElementById("login-password").value,
      });
      break;

    case "register":
      url = `${baseUrl}/auth/register`;
      method = "POST";
      body = JSON.stringify({
        username: document.getElementById("register-username").value,
        password: document.getElementById("register-password").value,
      });
      break;

    case "get-all-products":
      url = `${baseUrl}/products`;

      // Add query parameters if specified
      const category = document.getElementById("products-category").value;
      const search = document.getElementById("products-search").value;

      if (category) {
        url += url.includes("?")
          ? `&category=${encodeURIComponent(category)}`
          : `?category=${encodeURIComponent(category)}`;
      }

      if (search) {
        url += url.includes("?")
          ? `&search=${encodeURIComponent(search)}`
          : `?search=${encodeURIComponent(search)}`;
      }

      method = "GET";
      break;

    // Add more cases for other endpoints as needed
  }

  if (!url || !method) {
    alert("Endpoint not implemented for testing");
    return;
  }

  // Show response container
  const responseContainer = document.querySelector(
    `#try-it-${endpoint} .response-container`
  );
  if (responseContainer) {
    responseContainer.style.display = "block";
  }

  // Show "Loading..." in response
  const responseElement = document.getElementById(`${endpoint}-response`);
  if (responseElement) {
    responseElement.textContent = "Loading...";
  }

  // Make the API request
  fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      ...(token && method !== "POST" && !url.includes("/auth/")
        ? { Authorization: `Bearer ${token}` }
        : {}),
    },
    body: method !== "GET" ? body : undefined,
  })
    .then((response) => {
      // Get response status
      const status = response.status;

      // Parse response as JSON if possible
      return response.text().then((text) => {
        try {
          return {
            status,
            data: JSON.parse(text),
          };
        } catch (e) {
          return {
            status,
            data: text,
          };
        }
      });
    })
    .then(({ status, data }) => {
      // Format response
      let formattedResponse;
      if (typeof data === "object") {
        formattedResponse = JSON.stringify(data, null, 2);

        // Save token if login was successful
        if (endpoint === "login" && status === 200 && data.token) {
          localStorage.setItem("api_token", data.token);
        }
      } else {
        formattedResponse = data;
      }

      // Set response with status
      if (responseElement) {
        responseElement.textContent = `Status: ${status}\n\n${formattedResponse}`;
        hljs.highlightElement(responseElement);
      }
    })
    .catch((error) => {
      if (responseElement) {
        responseElement.textContent = `Error: ${error.message}`;
      }
    });
}

// Create a simple logo SVG
window.addEventListener("load", function () {
  if (!document.querySelector(".logo img").complete) {
    // Create an SVG logo
    const svgLogo = `
            <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect width="40" height="40" rx="8" fill="#0366D6"/>
                <path d="M12 20H28M20 12V28" stroke="white" stroke-width="4" stroke-linecap="round"/>
            </svg>
        `;

    // Create a data URL
    const dataUrl = `data:image/svg+xml;charset=utf-8,${encodeURIComponent(
      svgLogo
    )}`;

    // Set the data URL as the src
    document.querySelector(".logo img").src = dataUrl;
  }
});
