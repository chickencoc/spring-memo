function memoCsrfHeaders(headers) {
	const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
	const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
	const result = Object.assign({}, headers || {});

	if(csrfToken && csrfHeader) {
		result[csrfHeader] = csrfToken;
	}

	return result;
}
