// Form Handler for AJAX submissions

document.addEventListener('DOMContentLoaded', function() {
    const personelForm = document.getElementById('personelForm');
    if (personelForm) {
        personelForm.addEventListener('submit', handlePersonelForm);
    }
});

function handlePersonelForm(e) {
    e.preventDefault();
    
    const form = e.target;
    const formData = new FormData(form);
    
    // Convert FormData to object and handle empty strings
    const data = {};
    for (let [key, value] of formData.entries()) {
        // Skip empty strings for optional fields
        if (value === '') {
            // Skip optional fields that are empty
            if (key === 'department' || key === 'photoUrl' || key === 'hireDate' || key === 'salary') {
                continue;
            }
            // For number fields, set default values if empty
            if (key === 'annualLeaveQuota') {
                data[key] = 14;
                continue;
            }
            if (key === 'usedLeaveDays') {
                data[key] = 0;
                continue;
            }
        }
        
        // Convert number fields
        if (key === 'salary' && value !== '') {
            const salaryValue = parseFloat(value);
            if (!isNaN(salaryValue)) {
                data[key] = salaryValue;
            }
        } else if (key === 'annualLeaveQuota' && value !== '') {
            const quotaValue = parseInt(value);
            if (!isNaN(quotaValue)) {
                data[key] = quotaValue;
            }
        } else if (key === 'usedLeaveDays' && value !== '') {
            const usedValue = parseInt(value);
            if (!isNaN(usedValue)) {
                data[key] = usedValue;
            }
        } else if (key === 'id' && value !== '') {
            const idValue = parseInt(value);
            if (!isNaN(idValue)) {
                data[key] = idValue;
            }
        } else if (key === 'hireDate' && value !== '') {
            // Keep date as string (ISO format: YYYY-MM-DD)
            data[key] = value;
        } else if (value !== '') {
            data[key] = value;
        }
    }
    
    // Get entity ID
    const entityId = data.id;
    const url = entityId ? `/api/personel/${entityId}` : '/api/personel';
    const method = entityId ? 'PUT' : 'POST';
    
    // Show loading
    const submitBtn = form.querySelector('button[type="submit"]');
    const originalText = submitBtn.innerHTML;
    if (typeof CorpERP !== 'undefined') {
        CorpERP.showLoading(submitBtn);
    } else {
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Kaydediliyor...';
    }
    
    // Debug: Log the data being sent
    console.log('Sending data:', JSON.stringify(data, null, 2));
    
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(async response => {
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Server error response:', errorText);
            throw new Error(errorText || 'İşlem başarısız oldu');
        }
        return response.json();
    })
    .then(data => {
        // Success - redirect to list page
        window.location.href = '/personel';
    })
    .catch(error => {
        console.error('Error:', error);
        if (typeof CorpERP !== 'undefined') {
            CorpERP.hideLoading(submitBtn, originalText);
        } else {
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;
        }
        alert('Hata: ' + (error.message || 'Bir hata oluştu. Lütfen tekrar deneyin.'));
    });
}
