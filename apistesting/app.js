// Base API URL
const API_BASE_URL = 'http://localhost:9090/api/degree-requests';

// Common function for API calls
async function apiCall(endpoint, method = 'GET', body = null, headers = {}) {
    try {
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json',
                ...headers
            }
        };

        if (body && !(body instanceof FormData)) {
            options.body = JSON.stringify(body);
        } else if (body instanceof FormData) {
            delete options.headers['Content-Type'];
            options.body = body;
        }

        const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'API request failed');
        }

        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        showMessage(`Error: ${error.message}`, 'error');
        throw error;
    }
}

// Create Degree Request
async function createDegreeRequest(formData) {
    try {
        const form = new FormData();
        form.append('data', new Blob([JSON.stringify({
            program: formData.program,
            rollNumber: formData.rollNumber,
            passingYear: parseInt(formData.passingYear),
            cgpa: parseFloat(formData.cgpa),
            remarks: formData.remarks
        })], { type: 'application/json' }));

        if (formData.document) {
            form.append('documents', formData.document);
        }

        const result = await apiCall('', 'POST', form);

        showMessage('Degree request created successfully!', 'success');
        return result.data;
    } catch (error) {
        console.error('Create request error:', error);
        throw error;
    }
}

// Get requests by university
async function getByUniversity(universityId, page = 0, size = 10, sortBy = 'createdAt', sortDir = 'desc') {
    try {
        const endpoint = `/university/${universityId}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`;
        const result = await apiCall(endpoint);
        return result.data;
    } catch (error) {
        console.error('Get by university error:', error);
        throw error;
    }
}

// Get requests by university and status
async function getByUniversityAndStatus(universityId, status, page = 0, size = 10, sortBy = 'createdAt', sortDir = 'desc') {
    try {
        const endpoint = `/university/${universityId}/status/${status}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`;
        const result = await apiCall(endpoint);
        return result.data;
    } catch (error) {
        console.error('Get by university and status error:', error);
        throw error;
    }
}

// Get requests by student
async function getByStudent(studentId, page = 0, size = 10, sortBy = 'createdAt', sortDir = 'desc') {
    try {
        const endpoint = `/student/${studentId}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`;
        const result = await apiCall(endpoint);
        return result.data;
    } catch (error) {
        console.error('Get by student error:', error);
        throw error;
    }
}

// Get requests by student and status
async function getByStudentAndStatus(studentId, status, page = 0, size = 10, sortBy = 'createdAt', sortDir = 'desc') {
    try {
        const endpoint = `/student/${studentId}/status/${status}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`;
        const result = await apiCall(endpoint);
        return result.data;
    } catch (error) {
        console.error('Get by student and status error:', error);
        throw error;
    }
}

// Get requests by status
async function getByStatus(status, page = 0, size = 10, sortBy = 'createdAt', sortDir = 'desc') {
    try {
        const endpoint = `/status/${status}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`;
        const result = await apiCall(endpoint);
        return result.data;
    } catch (error) {
        console.error('Get by status error:', error);
        throw error;
    }
}

// Utility function to show messages
function showMessage(message, type = 'info') {
    // Create message element
    const messageDiv = document.createElement('div');
    messageDiv.textContent = message;
    messageDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 25px;
        border-radius: 5px;
        color: white;
        font-weight: bold;
        z-index: 1000;
        animation: slideIn 0.3s ease;
    `;

    if (type === 'success') {
        messageDiv.style.backgroundColor = '#4CAF50';
    } else if (type === 'error') {
        messageDiv.style.backgroundColor = '#f44336';
    } else {
        messageDiv.style.backgroundColor = '#2196F3';
    }

    document.body.appendChild(messageDiv);

    // Remove message after 3 seconds
    setTimeout(() => {
        messageDiv.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => document.body.removeChild(messageDiv), 300);
    }, 3000);
}

// Add CSS animations
function addMessageStyles() {
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
        @keyframes slideOut {
            from { transform: translateX(0); opacity: 1; }
            to { transform: translateX(100%); opacity: 0; }
        }
    `;
    document.head.appendChild(style);
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', function () {
    addMessageStyles();

    // Add global event listeners if needed
    if (typeof window.initApp === 'function') {
        window.initApp();
    }
});