// CORP ERP v3.0 - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Form validation
    const forms = document.querySelectorAll('form[needs-validation]');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });
});

// Utility Functions
const CorpERP = {
    showLoading: function(element) {
        if (element) {
            element.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Yükleniyor...';
            element.disabled = true;
        }
    },

    hideLoading: function(element, originalText) {
        if (element) {
            element.innerHTML = originalText;
            element.disabled = false;
        }
    },

    formatCurrency: function(amount) {
        return new Intl.NumberFormat('tr-TR', {
            style: 'currency',
            currency: 'TRY'
        }).format(amount);
    },

    formatDate: function(date) {
        return new Intl.DateTimeFormat('tr-TR').format(new Date(date));
    }
};

